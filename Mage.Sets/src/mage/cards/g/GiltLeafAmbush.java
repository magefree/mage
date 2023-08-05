package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfWarriorToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class GiltLeafAmbush extends CardImpl {

    public GiltLeafAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{2}{G}");
        this.subtype.add(SubType.ELF);

        // Create two 1/1 green Elf Warrior creature tokens into play. Clash with an opponent. If you win, those creatures gain deathtouch until end of turn
        this.getSpellAbility().addEffect(new GiltLeafAmbushCreateTokenEffect());
    }

    private GiltLeafAmbush(final GiltLeafAmbush card) {
        super(card);
    }

    @Override
    public GiltLeafAmbush copy() {
        return new GiltLeafAmbush(this);
    }
}

class GiltLeafAmbushCreateTokenEffect extends OneShotEffect {

    public GiltLeafAmbushCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create two 1/1 green Elf Warrior creature tokens. Clash with an opponent. If you win, those creatures gain deathtouch until end of turn";
    }

    public GiltLeafAmbushCreateTokenEffect(final GiltLeafAmbushCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public GiltLeafAmbushCreateTokenEffect copy() {
        return new GiltLeafAmbushCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new ElfWarriorToken(), 2);
            effect.apply(game, source);
            if (new ClashEffect().apply(game, source)) {
                for (UUID tokenId : effect.getLastAddedTokenIds()) {
                    Permanent token = game.getPermanent(tokenId);
                    if (token != null) {
                        ContinuousEffect continuousEffect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
                        continuousEffect.setTargetPointer(new FixedTarget(tokenId));
                        game.addEffect(continuousEffect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
