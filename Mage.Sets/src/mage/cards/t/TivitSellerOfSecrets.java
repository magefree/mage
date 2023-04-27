package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.VoteEvent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TivitSellerOfSecrets extends CardImpl {

    public TivitSellerOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new GenericManaCost(3), false));

        // Council's dilemma — Whenever Tivit enters the battlefield or deals combat damage to a player, starting with you, each player votes for evidence or bribery. For each evidence vote, investigate. For each bribery vote, create a Treasure token.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD, new TivitSellerOfSecretsEffect(), false,
                "Whenever {this} enters the battlefield or deals combat damage to a player, ",
                new EntersBattlefieldTriggeredAbility(null, false),
                new DealsCombatDamageToAPlayerTriggeredAbility(null, false)
        ).setAbilityWord(AbilityWord.COUNCILS_DILEMMA));

        // While voting, you may vote an additional time.
        this.addAbility(new SimpleStaticAbility(new TivitSellerOfSecretsReplacementEffect()));
    }

    private TivitSellerOfSecrets(final TivitSellerOfSecrets card) {
        super(card);
    }

    @Override
    public TivitSellerOfSecrets copy() {
        return new TivitSellerOfSecrets(this);
    }
}

class TivitSellerOfSecretsEffect extends OneShotEffect {

    TivitSellerOfSecretsEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for evidence or bribery. For each evidence vote, " +
                "investigate. For each bribery vote, create a Treasure token";
    }

    private TivitSellerOfSecretsEffect(final TivitSellerOfSecretsEffect effect) {
        super(effect);
    }

    @Override
    public TivitSellerOfSecretsEffect copy() {
        return new TivitSellerOfSecretsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TwoChoiceVote vote = new TwoChoiceVote(
                "Evidence (investigate)", "Bribery (treasure)", Outcome.Detriment
        );
        vote.doVotes(source, game);
        int evidenceCounter = vote.getVoteCount(true);
        int briberyCounter = vote.getVoteCount(false);
        if (evidenceCounter > 0) {
            new InvestigateEffect(evidenceCounter).apply(game, source);
        }
        if (briberyCounter > 0) {
            new TreasureToken().putOntoBattlefield(briberyCounter, game, source);
        }
        return evidenceCounter + briberyCounter > 0;
    }
}

class TivitSellerOfSecretsReplacementEffect extends ReplacementEffectImpl {

    TivitSellerOfSecretsReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "while voting, you may vote an additional time";
    }

    private TivitSellerOfSecretsReplacementEffect(final TivitSellerOfSecretsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TivitSellerOfSecretsReplacementEffect copy() {
        return new TivitSellerOfSecretsReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VOTE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((VoteEvent) event).incrementOptionalExtraVotes();
        return false;
    }
}
