package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LitaLittleOrphanAmphibian extends CardImpl {

    public LitaLittleOrphanAmphibian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Alliance -- Whenever another creature you control enters, choose one that hasn't been chosen this turn.
        // * Put a +1/+1 counter on Lita.
        Ability ability = new AllianceAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.setModeTag("put +1/+1 counter");
        ability.getModes().setLimitUsageByOnce(true);

        // * Create a Food token.
        ability.addMode(new Mode(new CreateTokenEffect(new FoodToken())).setModeTag("create a Food token"));

        // * Scry 1.
        ability.addMode(new Mode(new ScryEffect(1)).setModeTag("scry 1"));

        this.addAbility(ability.addHint(ModesAlreadyUsedHint.instance));
    }

    private LitaLittleOrphanAmphibian(final LitaLittleOrphanAmphibian card) {
        super(card);
    }

    @Override
    public LitaLittleOrphanAmphibian copy() {
        return new LitaLittleOrphanAmphibian(this);
    }
}
