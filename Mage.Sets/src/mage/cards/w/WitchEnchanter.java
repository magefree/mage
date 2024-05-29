package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WitchEnchanter extends ModalDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public WitchEnchanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARLOCK}, "{3}{W}",
                "Witch-Blessed Meadow", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Witch Enchanter
        // Creature — Human Warlock
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(2));

        // When Witch Enchanter enters the battlefield, destroy target artifact or enchantment an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Witch-Blessed Meadow
        // Land

        // As Witch-Blessed Meadow enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private WitchEnchanter(final WitchEnchanter card) {
        super(card);
    }

    @Override
    public WitchEnchanter copy() {
        return new WitchEnchanter(this);
    }
}
