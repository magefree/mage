package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PinnacleMonk extends ModalDoubleFacedCard {

    public PinnacleMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DJINN, SubType.MONK}, "{3}{R}{R}",
                "Mystic Peak", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Pinnacle Monk
        // Creature — Djinn Monk
        this.getLeftHalfCard().setPT(new MageInt(2), new MageInt(2));

        // Prowess
        this.getLeftHalfCard().addAbility(new ProwessAbility());

        // When Pinnacle Monk enters the battlefield, return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Mystic Peak
        // Land

        // As Mystic Peak enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private PinnacleMonk(final PinnacleMonk card) {
        super(card);
    }

    @Override
    public PinnacleMonk copy() {
        return new PinnacleMonk(this);
    }
}
