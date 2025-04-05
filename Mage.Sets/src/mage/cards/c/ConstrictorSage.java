package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConstrictorSage extends CardImpl {

    public ConstrictorSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Renew -- {2}{U}, Exile this card from your graveyard: Tap target creature an opponent controls and put a stun counter on it. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(Zone.GRAVEYARD, new TapTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.RENEW));
    }

    private ConstrictorSage(final ConstrictorSage card) {
        super(card);
    }

    @Override
    public ConstrictorSage copy() {
        return new ConstrictorSage(this);
    }
}
