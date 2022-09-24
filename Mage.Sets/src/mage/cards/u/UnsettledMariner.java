package mage.cards.u;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.TargetOfOpponentsSpellOrAbilityTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnsettledMariner extends CardImpl {

    public UnsettledMariner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls,
        // counter that spell or ability unless its controller pays {1}.
        this.addAbility(new TargetOfOpponentsSpellOrAbilityTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(1))));
    }

    private UnsettledMariner(final UnsettledMariner card) {
        super(card);
    }

    @Override
    public UnsettledMariner copy() {
        return new UnsettledMariner(this);
    }
}
