package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightwhorlHermit extends CardImpl {

    public NightwhorlHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Threshold -- As long as seven or more cards are in your graveyard, Nightwhorl Hermit gets +1/+0 and can't be blocked.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "as long as seven or more cards are in your graveyard, {this} gets +1/+0"
        ));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), ThresholdCondition.instance, "and can't be blocked"
        ));
        this.addAbility(ability.setAbilityWord(AbilityWord.THRESHOLD));
    }

    private NightwhorlHermit(final NightwhorlHermit card) {
        super(card);
    }

    @Override
    public NightwhorlHermit copy() {
        return new NightwhorlHermit(this);
    }
}
