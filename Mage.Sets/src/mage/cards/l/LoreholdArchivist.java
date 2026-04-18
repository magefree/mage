package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class LoreholdArchivist extends PrepareCard {

    private static final CardsInControllerGraveyardCondition condition
            = new CardsInControllerGraveyardCondition(3, StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE);
    private static final Hint hint = new ValueHint(
            "Artifact or creature cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE)
    );

    public LoreholdArchivist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}", "Restore Relic", new CardType[]{CardType.SORCERY}, "{2}{R}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, if there are three or more artifact and/or creature cards in your graveyard, this creature becomes prepared.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new BecomePreparedSourceEffect())
            .withInterveningIf(condition)
            .addHint(hint));

        // Restore Relic
        // Sorcery {2}{R}{W}
        // Exile target artifact or creature card from your graveyard. Create a token that's a copy of it.
        this.getSpellCard().getSpellAbility().addEffect(new RestoreRelicEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE));
    }

    private LoreholdArchivist(final LoreholdArchivist card) {
        super(card);
    }

    @Override
    public LoreholdArchivist copy() {
        return new LoreholdArchivist(this);
    }
}

class RestoreRelicEffect extends OneShotEffect {

    RestoreRelicEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target artifact or creature card from your graveyard. Create a token that's a copy of it.";
    }

    private RestoreRelicEffect(final RestoreRelicEffect effect) {
        super(effect);
    }

    @Override
    public RestoreRelicEffect copy() {
        return new RestoreRelicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setTargetPointer(new FixedTarget(card, game));
        return effect.apply(game, source);
    }
}
