
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author TheElk801
 */
public final class OtarianJuggernaut extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Walls");

    static {
        filter.add(new SubtypePredicate(SubType.WALL));
    }

    public OtarianJuggernaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Otarian Juggernaut can't be blocked by Walls.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Threshold - As long as seven or more cards are in your graveyard, Otarian Juggernaut gets +3/+0 and attacks each combat if able.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                new CardsInControllerGraveCondition(7),
                "As long as seven or more cards are in your graveyard, {this} gets +3/+0"));
        Effect effect = new ConditionalContinuousEffect(
                new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield, true),
                new CardsInControllerGraveCondition(7),
                "and attacks each combat if able"
        );
        ability.addEffect(effect);
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public OtarianJuggernaut(final OtarianJuggernaut card) {
        super(card);
    }

    @Override
    public OtarianJuggernaut copy() {
        return new OtarianJuggernaut(this);
    }
}
