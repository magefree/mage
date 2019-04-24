
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class RegalBehemoth extends CardImpl {

    public RegalBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Regal Behemoth enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // Whenever you tap a land for mana while you're the monarch, add one mana of any color.
        ManaEffect manaEffect = new AddManaOfAnyColorEffect();
        manaEffect.setText("add one mana of any color <i>(in addition to the mana the land produces)</i>.");
        ManaEffect effect = manaEffect;
        this.addAbility(new RegalBehemothTriggeredManaAbility(
                effect, new FilterControlledLandPermanent("you tap a land")));
    }

    public RegalBehemoth(final RegalBehemoth card) {
        super(card);
    }

    @Override
    public RegalBehemoth copy() {
        return new RegalBehemoth(this);
    }
}

class RegalBehemothTriggeredManaAbility extends TriggeredManaAbility {

    private final FilterPermanent filter;

    public RegalBehemothTriggeredManaAbility(ManaEffect effect, FilterPermanent filter) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
    }

    public RegalBehemothTriggeredManaAbility(RegalBehemothTriggeredManaAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getMonarchId() != null
                && isControlledBy(game.getMonarchId())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null 
                    && getControllerId() != null
                    && filter.match(permanent, getSourceId(), getControllerId(), game)) {
                ManaEvent mEvent = (ManaEvent) event;
                for (Effect effect : getEffects()) {
                    effect.setValue("mana", mEvent.getMana());
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public RegalBehemothTriggeredManaAbility copy() {
        return new RegalBehemothTriggeredManaAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a land for mana while you're the monarch, " + super.getRule();
    }
}
