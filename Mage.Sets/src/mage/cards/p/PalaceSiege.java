package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ModeChoice;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PalaceSiege extends CardImpl {

    public PalaceSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // As Palace Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.KHANS, ModeChoice.DRAGONS)));

        // * Khans - At the beginning of your upkeep, return target creature card from your graveyard to your hand.
        Ability ability1 = new BeginningOfUpkeepTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability1.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability1, ModeChoice.KHANS)));

        // * Dragons - At the beginning of your upkeep, each opponent loses 2 life and you gain 2 life.
        Ability ability2 = new BeginningOfUpkeepTriggeredAbility(new LoseLifeOpponentsEffect(2));
        ability2.addEffect(new GainLifeEffect(2).setText("and you gain 2 life"));
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability2, ModeChoice.DRAGONS)));

    }

    private PalaceSiege(final PalaceSiege card) {
        super(card);
    }

    @Override
    public PalaceSiege copy() {
        return new PalaceSiege(this);
    }
}
