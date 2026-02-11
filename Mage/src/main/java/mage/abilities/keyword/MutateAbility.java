package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

public class MutateAbility extends SpellAbility {

    public MutateAbility(Card card, String manaString) {
        super(new ManaCostsImpl<>(manaString), card.getName() + " using mutate");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = TimingRule.SORCERY;

        // Allow casting from hand or graveyard
        this.getSpellAbility().getZone().add(Zone.HAND);
        this.getSpellAbility().getZone().add(Zone.GRAVEYARD);

        // Set target: non-Human creature you control
        TargetCreaturePermanent target = new TargetCreaturePermanent(1, 1,
            (Permanent target1, Game game) -> target1.isCreature() && !target1.hasSubtype(SubType.HUMAN), false);
        this.addTarget(target);
    }

    private MutateAbility(final MutateAbility ability) {
        super(ability);
    }

    @Override
    public MutateAbility copy() {
        return new MutateAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Mutate " + getManaCostsToPay().getText() + " <i>(If you cast this spell for its mutate cost, " +
                "put it over or under target non-Human creature you own. " +
                "They mutate into the creature on top plus all abilities from under it.)</i>";
    }

    @Override
    public boolean resolve(Game game) {
        Card source = game.getCard(this.getSourceId());
        Player controller = game.getPlayer(this.getControllerId());

        if (source == null || controller == null) {
            return false;
        }

        // Find target creature
        Permanent targetCreature = game.getPermanent(this.getFirstTarget());

        if (targetCreature == null || !targetCreature.isCreature() || targetCreature.hasSubtype(SubType.HUMAN)) {
            // Target invalid - fizzle
            return false;
        }

        // Ask controller whether to place mutating creature on top or bottom
        boolean onTop = controller.chooseUse(
            mage.constants.Outcome.Neutral,
            "Place " + source.getName() + " on top of " + targetCreature.getName() + "?",
            this,
            game
        );

        // Move source card to be with target (merge)
        targetCreature.mergeCard(source, onTop, game);

        // Fire mutate event
        GameEvent event = new GameEvent(GameEvent.EventType.MUTATE, targetCreature.getId(), this.getSourceId(), this.getControllerId());
        game.fireEvent(event);

        return true;
    }
}
