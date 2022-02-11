package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NadiersNightblade extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public NadiersNightblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a token you control leaves the battlefield, each opponent loses 1 life and you gain 1 life.
        Ability ability = new LeavesBattlefieldAllTriggeredAbility(new LoseLifeOpponentsEffect(1), filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private NadiersNightblade(final NadiersNightblade card) {
        super(card);
    }

    @Override
    public NadiersNightblade copy() {
        return new NadiersNightblade(this);
    }
}
