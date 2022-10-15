package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.StanggTwinToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class StanggEchoWarrior extends CardImpl {

    public StanggEchoWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Stangg, Echo Warrior attacks, create Stangg Twin, a legendary 3/4 red and green Human Warrior creature token. It enters the battlefield tapped and attacking. For each Aura and Equipment attached to Stangg, create a token that's a copy of it attached to Stangg Twin. Sacrifice all tokens created this way at the beginning of the next end step.
        this.addAbility(new AttacksTriggeredAbility(new StanggEchoWarriorEffect()));
    }

    private StanggEchoWarrior(final StanggEchoWarrior card) {
        super(card);
    }

    @Override
    public StanggEchoWarrior copy() {
        return new StanggEchoWarrior(this);
    }
}

class StanggEchoWarriorEffect extends OneShotEffect {

    StanggEchoWarriorEffect() {
        super(Outcome.Benefit);
        staticText = "create Stangg Twin, a legendary 3/4 red and green Human Warrior creature token. " +
                "It enters the battlefield tapped and attacking. For each Aura and Equipment attached " +
                "to {this}, create a token that's a copy of it attached to Stangg Twin. " +
                "Sacrifice all tokens created this way at the beginning of the next end step";
    }

    private StanggEchoWarriorEffect(final StanggEchoWarriorEffect effect) {
        super(effect);
    }

    @Override
    public StanggEchoWarriorEffect copy() {
        return new StanggEchoWarriorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> tokens = makeTokens(game, source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect().setTargetPointer(new FixedTargets(tokens, game))
        ), source);
        return true;
    }

    static List<Permanent> makeTokens(Game game, Ability source) {
        Token token = new StanggTwinToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
        List<Permanent> toSacrifice = token
                .getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return toSacrifice;
        }
        Set<Permanent> attachments = sourcePermanent
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> p.hasSubtype(SubType.AURA, game) || p.hasSubtype(SubType.EQUIPMENT, game))
                .collect(Collectors.toSet());
        if (attachments.isEmpty()) {
            return toSacrifice;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            for (Permanent attachment : attachments) {
                CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
                effect.setSavedPermanent(attachment);
                effect.apply(game, source);
                effect.getAddedPermanents().stream().map(t -> permanent.addAttachment(t.getId(), source, game));
                toSacrifice.addAll(effect.getAddedPermanents());
            }
        }
        return toSacrifice;
    }
}
