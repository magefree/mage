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

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ElusiveTormentor extends TransformingDoubleFacedCard {

    public ElusiveTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.WIZARD}, "{2}{B}{B}",
                "Insidious Mist",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL}, "U"
        );
        this.getLeftHalfCard().setPT(4, 4);
        this.getRightHalfCard().setPT(0, 1);

        // {1}, Discard a card: Transform Elusive Tormentor.
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.getLeftHalfCard().addAbility(ability);

        // Insidious Mist
        // Hexproof
        this.getRightHalfCard().addAbility(HexproofAbility.getInstance());

        // Indestructible
        this.getRightHalfCard().addAbility(IndestructibleAbility.getInstance());

        // Insideous Mist can't block and can't be blocked.
        ability = new SimpleStaticAbility(new CantBlockSourceEffect(Duration.WhileOnBattlefield));
        ability.addEffect(new CantBeBlockedSourceEffect().setText("and can't be blocked"));
        this.getRightHalfCard().addAbility(ability);

        // Whenever Insideous Mist attacks and isn't blocked, you may pay {2}{B}. If you do, transform it.
        this.getRightHalfCard().addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect().setText("transform it"), new ManaCostsImpl<>("{2}{B}")
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
