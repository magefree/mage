package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseOfFoolsWisdom extends CardImpl {

    public CurseOfFoolsWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted player draws a card, they lose 2 life and you gain 2 life.
        this.addAbility(new CurseOfFoolsWisdomTriggeredAbility());

        // Madness {3}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{3}{B}")));
    }

    private CurseOfFoolsWisdom(final CurseOfFoolsWisdom card) {
        super(card);
    }

    @Override
    public CurseOfFoolsWisdom copy() {
        return new CurseOfFoolsWisdom(this);
    }
}

class CurseOfFoolsWisdomTriggeredAbility extends TriggeredAbilityImpl {

    CurseOfFoolsWisdomTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private CurseOfFoolsWisdomTriggeredAbility(final CurseOfFoolsWisdomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(getSourceId());
        if (enchantment == null || !event.getPlayerId().equals(enchantment.getAttachedTo())) {
            return false;
        }
        this.getEffects().clear();
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setTargetPointer(new FixedTarget(event.getPlayerId(), game));
        this.addEffect(effect);
        this.addEffect(new GainLifeEffect(2));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted player draws a card, they lose 2 life and you gain 2 life.";
    }

    @Override
    public CurseOfFoolsWisdomTriggeredAbility copy() {
        return new CurseOfFoolsWisdomTriggeredAbility(this);
    }

}