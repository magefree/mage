package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoshuaPhoenixsDominant extends CardImpl {

    public JoshuaPhoenixsDominant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.p.PhoenixWardenOfFire.class;

        // When Joshua enters, discard up to two cards, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardAndDrawThatManyEffect(2)));

        // {3}{R}{W}, {T}: Exile Joshua, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{3}{R}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private JoshuaPhoenixsDominant(final JoshuaPhoenixsDominant card) {
        super(card);
    }

    @Override
    public JoshuaPhoenixsDominant copy() {
        return new JoshuaPhoenixsDominant(this);
    }
}
