package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.replacement.CreaturesAreExiledOnDeathReplacementEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class CorpseweaverProdigy extends CardImpl {

    public CorpseweaverProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new CreaturesAreExiledOnDeathReplacementEffect(
            StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        )));

        // Infusion — At the beginning of your second main phase, if you gained life this turn, conjure a card named Bridge from Below into your graveyard.
        TriggeredAbility ability = new BeginningOfSecondMainTriggeredAbility(
            new ConjureCardEffect("Bridge from Below", Zone.GRAVEYARD, 1), false
        );
        ability.withInterveningIf(YouGainedLifeCondition.getZero());
        ability.setAbilityWord(AbilityWord.INFUSION);
        ability.addHint(ControllerGainedLifeCount.getHint());
        this.addAbility(ability, new PlayerGainedLifeWatcher());
    }

    private CorpseweaverProdigy(final CorpseweaverProdigy card) {
        super(card);
    }

    @Override
    public CorpseweaverProdigy copy() {
        return new CorpseweaverProdigy(this);
    }
}
