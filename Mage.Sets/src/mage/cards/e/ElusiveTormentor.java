package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ElusiveTormentor extends TransformingDoubleFacedCard {

    public ElusiveTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.WIZARD}, "{2}{B}{B}",
                "Insidious Mist",
                new SuperType[]{}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL}, "U"
        );

        // Elusive Tormentor
        this.getLeftHalfCard().setPT(4, 4);

        // {1}, Discard a card: Transform Elusive Tormentor.
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.getLeftHalfCard().addAbility(ability);


        // Insidious Mist
        this.getRightHalfCard().setPT(0, 1);

        // Hexproof
        this.getRightHalfCard().addAbility(HexproofAbility.getInstance());

        // Indestructible
        this.getRightHalfCard().addAbility(IndestructibleAbility.getInstance());

        // Insidious Mist can't block and can't be blocked.
        Ability staticAbility = new SimpleStaticAbility(new CantBlockSourceEffect(Duration.WhileOnBattlefield));
        staticAbility.addEffect(new CantBeBlockedSourceEffect().setText("and can't be blocked"));
        this.getRightHalfCard().addAbility(staticAbility);

        // Whenever Insidious Mist attacks and isn't blocked, you may pay {2}{B}. If you do, transform it.
        this.getRightHalfCard().addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect().setText("transform it"), new ManaCostsImpl<>("{2}{B}"), "Pay {2}{B} to transform?"
        )));
    }

    private ElusiveTormentor(final ElusiveTormentor card) {
        super(card);
    }

    @Override
    public ElusiveTormentor copy() {
        return new ElusiveTormentor(this);
    }
}
