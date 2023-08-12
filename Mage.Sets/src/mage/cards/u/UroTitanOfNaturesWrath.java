package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UroTitanOfNaturesWrath extends CardImpl {

    public UroTitanOfNaturesWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Uro enters the battlefield, sacrifice it unless it escaped.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UroTitanOfNaturesWrathEffect()));

        // Whenever Uro enters the battlefield or attacks, you gain 3 life and draw a card, then you may put a land card from your hand onto the battlefield.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GainLifeEffect(3));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A).concatBy(", then"));
        this.addAbility(ability);

        // Escape-{G}{G}{U}{U}, Exile five other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{G}{G}{U}{U}", 5));
    }

    private UroTitanOfNaturesWrath(final UroTitanOfNaturesWrath card) {
        super(card);
    }

    @Override
    public UroTitanOfNaturesWrath copy() {
        return new UroTitanOfNaturesWrath(this);
    }
}

class UroTitanOfNaturesWrathEffect extends OneShotEffect {

    UroTitanOfNaturesWrathEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice it unless it escaped";
    }

    private UroTitanOfNaturesWrathEffect(final UroTitanOfNaturesWrathEffect effect) {
        super(effect);
    }

    @Override
    public UroTitanOfNaturesWrathEffect copy() {
        return new UroTitanOfNaturesWrathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        if (EscapeAbility.wasCastedWithEscape(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter())) {
            return false;
        }
        return permanent.sacrifice(source, game);
    }
}
