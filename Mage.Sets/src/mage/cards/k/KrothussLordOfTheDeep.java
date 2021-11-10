package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KrothussLordOfTheDeep extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("another attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public KrothussLordOfTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Krothuss, Lord of the Deep attacks, create a tapped and attacking token that's a copy of another target attacking creature. If that creature is a Kraken, Leviathan, Octopus, or Serpent, create two of those tokens instead.
        Ability ability = new AttacksTriggeredAbility(new KrothussLordOfTheDeepEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KrothussLordOfTheDeep(final KrothussLordOfTheDeep card) {
        super(card);
    }

    @Override
    public KrothussLordOfTheDeep copy() {
        return new KrothussLordOfTheDeep(this);
    }
}

class KrothussLordOfTheDeepEffect extends OneShotEffect {

    KrothussLordOfTheDeepEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking token that's a copy of another target attacking creature. " +
                "If that creature is a Kraken, Leviathan, Octopus, or Serpent, create two of those tokens instead";
    }

    private KrothussLordOfTheDeepEffect(final KrothussLordOfTheDeepEffect effect) {
        super(effect);
    }

    @Override
    public KrothussLordOfTheDeepEffect copy() {
        return new KrothussLordOfTheDeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int count = permanent.hasSubtype(SubType.KRAKEN, game)
                || permanent.hasSubtype(SubType.LEVIATHAN, game)
                || permanent.hasSubtype(SubType.OCTOPUS, game)
                || permanent.hasSubtype(SubType.SERPENT, game) ? 2 : 1;
        return new CreateTokenCopyTargetEffect(
                null, null,
                false, count, true, true
        ).apply(game, source);
    }
}
