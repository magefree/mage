package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

public class MutateAbility extends SpellAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Human creature");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public MutateAbility(Card card, String manaString) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with mutate");
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.getManaCosts().clear();
        this.getManaCostsToPay().clear();
        this.addManaCost(new ManaCostsImpl(manaString));

        this.setRuleAtTheTop(true);

        TargetPermanent mutateTarget = new TargetCreaturePermanent(filter);
        this.addTarget(mutateTarget);

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new MutateEntersBattlefieldEffect());
        ability.setRuleVisible(false);
        addSubAbility(ability);
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

}

class MutateEntersBattlefieldEffect extends ReplacementEffectImpl {

    public MutateEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
    }

    public MutateEntersBattlefieldEffect(final MutateEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.ENTERS_THE_BATTLEFIELD_SELF == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent mutatePermanent = game.getPermanentEntering(source.getSourceId());
        if (mutatePermanent != null) {
            // I don't really know what this code does - copied from AttachEffect.
            // Let me know if this is unneeded.
            int zcc = game.getState().getZoneChangeCounter(mutatePermanent.getId());
            if (zcc == source.getSourceObjectZoneChangeCounter()
                    || zcc == source.getSourceObjectZoneChangeCounter() + 1
                    || zcc == source.getSourceObjectZoneChangeCounter() + 2) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    permanent.addMergedCard(source.getSourceId());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MutateEntersBattlefieldEffect copy() {
        return new MutateEntersBattlefieldEffect(this);
    }

}
