package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class NecroticOoze extends CardImpl {

    public NecroticOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // As long as Necrotic Ooze is on the battlefield, it has all 
        // activated abilities of all creature cards in all graveyards
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NecroticOozeEffect()));
    }

    private NecroticOoze(final NecroticOoze card) {
        super(card);
    }

    @Override
    public NecroticOoze copy() {
        return new NecroticOoze(this);
    }

    static class NecroticOozeEffect extends ContinuousEffectImpl {

        public NecroticOozeEffect() {
            super(Duration.WhileOnBattlefield,
                    Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            staticText = "As long as {this} is on the battlefield, "
                    + "it has all activated abilities of all creature cards in all graveyards";

            this.dependendToTypes.add(DependencyType.AddingAbility); // Yixlid Jailer
        }

        public NecroticOozeEffect(final NecroticOozeEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent perm = game.getPermanent(source.getSourceId());
            if (perm != null) {
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        for (Card card : player.getGraveyard().getCards(game)) {
                            if (card.isCreature(game)) {
                                for (Ability ability : card.getAbilities(game)) {
                                    if (ability instanceof ActivatedAbility
                                            && !perm.getAbilities().contains(ability)) {
                                        if (!perm.getAbilities().contains(ability)) {
                                            perm.addAbility(ability, source.getSourceId(), game);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public NecroticOozeEffect copy() {
            return new NecroticOozeEffect(this);
        }
    }

}
