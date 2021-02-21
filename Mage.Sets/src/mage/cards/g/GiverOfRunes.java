package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiverOfRunes extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GiverOfRunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Another target creature you control gains protection from colorless or from the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(new GiverOfRunesEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GiverOfRunes(final GiverOfRunes card) {
        super(card);
    }

    @Override
    public GiverOfRunes copy() {
        return new GiverOfRunes(this);
    }
}

class GiverOfRunesEffect extends OneShotEffect {

    private static final FilterObject filter = new FilterObject("colorless");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    GiverOfRunesEffect() {
        super(Outcome.Benefit);
        staticText = "Another target creature you control gains protection from colorless " +
                "or from the color of your choice until end of turn.";
    }

    private GiverOfRunesEffect(final GiverOfRunesEffect effect) {
        super(effect);
    }

    @Override
    public GiverOfRunesEffect copy() {
        return new GiverOfRunesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(outcome, "Give the targeted creature protection from colorless?", null, "Yes", "No (choose a color instead)", source, game)) {
            game.addEffect(new GainAbilityTargetEffect(new ProtectionAbility(filter), Duration.EndOfTurn), source);
            return true;
        }
        game.addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), source);
        return true;
    }
}
