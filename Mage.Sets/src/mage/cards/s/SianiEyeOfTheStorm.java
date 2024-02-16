package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SianiEyeOfTheStorm extends CardImpl {

    public SianiEyeOfTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Siani, Eye of the Storm attacks, scry X, where X is the number of attacking creatures with flying.
        this.addAbility(new AttacksTriggeredAbility(new SianiEyeOfTheStormEffect(), false));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private SianiEyeOfTheStorm(final SianiEyeOfTheStorm card) {
        super(card);
    }

    @Override
    public SianiEyeOfTheStorm copy() {
        return new SianiEyeOfTheStorm(this);
    }
}

class SianiEyeOfTheStormEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(AttackingPredicate.instance);
    }

    SianiEyeOfTheStormEffect() {
        super(Outcome.Benefit);
        staticText = "scry X, where X is the number of attacking creatures with flying";
    }

    private SianiEyeOfTheStormEffect(final SianiEyeOfTheStormEffect effect) {
        super(effect);
    }

    @Override
    public SianiEyeOfTheStormEffect copy() {
        return new SianiEyeOfTheStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        return count > 0 && player.scry(count, source, game);
    }
}
