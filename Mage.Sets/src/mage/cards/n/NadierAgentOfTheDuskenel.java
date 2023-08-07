package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NadierAgentOfTheDuskenel extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public NadierAgentOfTheDuskenel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a token you control leaves the battlefield, put a +1/+1 counter on Nadier, Agent of the Duskenel.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));

        // When Nadier leaves the battlefield, create a number of 1/1 green Elf Warrior creature tokens equal to its power.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new NadierAgentOfTheDuskenelEffect(), false));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private NadierAgentOfTheDuskenel(final NadierAgentOfTheDuskenel card) {
        super(card);
    }

    @Override
    public NadierAgentOfTheDuskenel copy() {
        return new NadierAgentOfTheDuskenel(this);
    }
}

class NadierAgentOfTheDuskenelEffect extends OneShotEffect {

    NadierAgentOfTheDuskenelEffect() {
        super(Outcome.Benefit);
        staticText = "create a number of 1/1 green Elf Warrior creature tokens equal to its power";
    }

    private NadierAgentOfTheDuskenelEffect(final NadierAgentOfTheDuskenelEffect effect) {
        super(effect);
    }

    @Override
    public NadierAgentOfTheDuskenelEffect copy() {
        return new NadierAgentOfTheDuskenelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object obj = getValue("permanentLeftBattlefield");
        if (!(obj instanceof Permanent)) {
            return false;
        }
        Permanent permanent = (Permanent) obj;
        if (!permanent.isCreature(game) || permanent.getPower().getValue() < 1) {
            return false;
        }
        return new ElfWarriorToken().putOntoBattlefield(permanent.getPower().getValue(), game, source, source.getControllerId()
        );
    }
}
