package mage.cards.p;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.OneOrMoreDamagePlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

/**
 *
 * @author Jmlundeen
 */
public final class ParapetThrasher extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DRAGON, "Dragons you control");
    private static final FilterPermanent artifactFilter = new FilterArtifactPermanent("artifact that opponent controls");

    public ParapetThrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Dragons you control deal combat damage to an opponent, choose one that hasn't been chosen this turn --
        // * Destroy target artifact that opponent controls.
        Ability ability = new OneOrMoreDamagePlayerTriggeredAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(),
                filter, true, true, SetTargetPointer.PLAYER, false)
                .setTriggerPhrase("Whenever one or more Dragons you control deal combat damage to an opponent, ");
        ability.addTarget(new TargetPermanent(artifactFilter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        ability.setModeTag("destroy artifact");
        ability.getModes().setLimitUsageByOnce(true);

        // * This creature deals 4 damage to each other opponent.
        Mode mode = new Mode(new ParapetThrasherDamageEffect());
        mode.setModeTag("damage opponents");
        ability.addMode(mode);

        // * Exile the top card of your library. You may play it this turn.
        Effect effect = new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn);
        effect.setText(effect.getText(null).replace("that card", "it"));
        Mode mode2 = new Mode(effect);
        mode2.setModeTag("exile top card");
        ability.addMode(mode2);
        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private ParapetThrasher(final ParapetThrasher card) {
        super(card);
    }

    @Override
    public ParapetThrasher copy() {
        return new ParapetThrasher(this);
    }
}

class ParapetThrasherDamageEffect extends OneShotEffect {

    ParapetThrasherDamageEffect() {
        super(Outcome.Benefit);
        this.staticText = "This creature deals 4 damage to each other opponent";
    }

    private ParapetThrasherDamageEffect(final ParapetThrasherDamageEffect effect) {
        super(effect);
    }

    @Override
    public ParapetThrasherDamageEffect copy() {
        return new ParapetThrasherDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID damagedOpponent = this.getTargetPointer().getFirst(game, source);
        MageObject object = game.getObject(source);
        if (object != null && damagedOpponent != null) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                if (!Objects.equals(playerId, damagedOpponent)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        int dealtDamage = opponent.damage(4, source.getSourceId(), source, game);
                        game.informPlayers(object.getLogName() + " deals " + dealtDamage + " damage to " + opponent.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
