PGDMP  )            
         |            staleks_tg_bot    16.1    16.1 #    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16413    staleks_tg_bot    DATABASE     �   CREATE DATABASE staleks_tg_bot WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Kazakhstan.1251';
    DROP DATABASE staleks_tg_bot;
                postgres    false            �            1259    16430 	   customers    TABLE     �   CREATE TABLE public.customers (
    id integer NOT NULL,
    name character varying(20),
    phone character varying(30),
    age integer
);
    DROP TABLE public.customers;
       public         heap    postgres    false            �            1259    16429    customers_id_seq    SEQUENCE     �   CREATE SEQUENCE public.customers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.customers_id_seq;
       public          postgres    false    218            �           0    0    customers_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.customers_id_seq OWNED BY public.customers.id;
          public          postgres    false    217            �            1259    16463    orders    TABLE     �   CREATE TABLE public.orders (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    customer_id integer DEFAULT 1
);
    DROP TABLE public.orders;
       public         heap    postgres    false            �            1259    16462    orders_id_seq    SEQUENCE     �   CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.orders_id_seq;
       public          postgres    false    220            �           0    0    orders_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;
          public          postgres    false    219            �            1259    16478 	   standards    TABLE     �   CREATE TABLE public.standards (
    id integer NOT NULL,
    user_id integer,
    image_name character varying(255) NOT NULL,
    image_path character varying(255) NOT NULL
);
    DROP TABLE public.standards;
       public         heap    postgres    false            �            1259    16477    standards_id_seq    SEQUENCE     �   CREATE SEQUENCE public.standards_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.standards_id_seq;
       public          postgres    false    222            �           0    0    standards_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.standards_id_seq OWNED BY public.standards.id;
          public          postgres    false    221            �            1259    16420    users    TABLE     u  CREATE TABLE public.users (
    id smallint NOT NULL,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    department character varying(30) NOT NULL,
    telegram_id bigint NOT NULL,
    CONSTRAINT users_check CHECK ((((first_name)::text <> ''::text) AND ((last_name)::text <> ''::text) AND ((department)::text <> ''::text)))
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16419    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    216            �           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    215            *           2604    16433    customers id    DEFAULT     l   ALTER TABLE ONLY public.customers ALTER COLUMN id SET DEFAULT nextval('public.customers_id_seq'::regclass);
 ;   ALTER TABLE public.customers ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    218    218            +           2604    16466 	   orders id    DEFAULT     f   ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);
 8   ALTER TABLE public.orders ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    220    220            -           2604    16481    standards id    DEFAULT     l   ALTER TABLE ONLY public.standards ALTER COLUMN id SET DEFAULT nextval('public.standards_id_seq'::regclass);
 ;   ALTER TABLE public.standards ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    222    222            )           2604    16423    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �          0    16430 	   customers 
   TABLE DATA           9   COPY public.customers (id, name, phone, age) FROM stdin;
    public          postgres    false    218   �%       �          0    16463    orders 
   TABLE DATA           7   COPY public.orders (id, name, customer_id) FROM stdin;
    public          postgres    false    220   2&       �          0    16478 	   standards 
   TABLE DATA           H   COPY public.standards (id, user_id, image_name, image_path) FROM stdin;
    public          postgres    false    222   n&       �          0    16420    users 
   TABLE DATA           S   COPY public.users (id, first_name, last_name, department, telegram_id) FROM stdin;
    public          postgres    false    216   �&       �           0    0    customers_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.customers_id_seq', 4, true);
          public          postgres    false    217            �           0    0    orders_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.orders_id_seq', 4, true);
          public          postgres    false    219            �           0    0    standards_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.standards_id_seq', 6, true);
          public          postgres    false    221            �           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 13, true);
          public          postgres    false    215            4           2606    16435    customers customers_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.customers DROP CONSTRAINT customers_pkey;
       public            postgres    false    218            6           2606    16469    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public            postgres    false    220            8           2606    16485    standards standards_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.standards
    ADD CONSTRAINT standards_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.standards DROP CONSTRAINT standards_pkey;
       public            postgres    false    222            0           2606    16426    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    216            2           2606    16428    users users_telegram_id_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_telegram_id_key UNIQUE (telegram_id);
 E   ALTER TABLE ONLY public.users DROP CONSTRAINT users_telegram_id_key;
       public            postgres    false    216            9           2606    16470    orders orders_customer_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id) ON DELETE SET DEFAULT;
 H   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_customer_id_fkey;
       public          postgres    false    218    220    4660            :           2606    16486     standards standards_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.standards
    ADD CONSTRAINT standards_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE RESTRICT;
 J   ALTER TABLE ONLY public.standards DROP CONSTRAINT standards_user_id_fkey;
       public          postgres    false    216    4656    222            �   '   x�3�t�O��".cΐ�\(ۄ387�$ʋ���� ��	�      �   ,   x�3�ttt�4�2�tvv�4�2�tqq�4�2�trr���qqq r��      �   s   x�]�A�  ���+��vW�?0�/�A��������IF��>�L�[:�\��Ը/ȧ"�M�s�_+`��f��5ۖz��9u[���n��ՏFώ�8�Qr��5��1 ��i*�      �   r   x�3��������,���MI�OI-(�4426153���24�tJͮJ,�tO��,ͩ��M-Í*.C#�A(��857)�"i\�Ɯ>~���PI���������)W� G(}     