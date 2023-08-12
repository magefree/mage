package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.AngelToken;
import mage.game.permanent.token.RedHumanToken;
import mage.game.permanent.token.SpiritBlueToken;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaintTraftAndRemKarolus extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that has convoke");

    static {
        filter.add(new AbilityPredicate(ConvokeAbility.class));
    }

    public SaintTraftAndRemKarolus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Saint Traft and Rem Karolus becomes tapped, create a 1/1 red Human creature token if this is the first time this ability has resolved this turn. If it's the second time, create a 1/1 blue Spirit creature token with flying. If it's the third time, create a 4/4 white Angel creature token with flying.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new IfAbilityHasResolvedXTimesEffect(
                Outcome.PutCreatureInPlay, 1, new CreateTokenEffect(new RedHumanToken())
        ).setText("create a 1/1 red Human creature token if this is the first time this ability has resolved this turn"));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.PutCreatureInPlay, 2, new CreateTokenEffect(new SpiritBlueToken())
        ).setText("If it's the second time, create a 1/1 blue Spirit creature token with flying"));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.PutCreatureInPlay, 3, new CreateTokenEffect(new AngelToken())
        ).setText("If it's the third time, create a 4/4 white Angel creature token with flying"));
        this.addAbility(ability, new AbilityResolvedWatcher());

        // Whenever you cast a spell that has convoke, untap Saint Traft and Rem Karolus.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false));
    }

    private SaintTraftAndRemKarolus(final SaintTraftAndRemKarolus card) {
        super(card);
    }

    @Override
    public SaintTraftAndRemKarolus copy() {
        return new SaintTraftAndRemKarolus(this);
    }
}
