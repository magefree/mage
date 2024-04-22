package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CatToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KembaKhaEnduring extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CAT, "Cat");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent("equipped creatures");

    static {
        filter3.add(EquippedPredicate.instance);
    }

    public KembaKhaEnduring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Kemba, Kha Enduring or another Cat enters the battlefield under your control, attach up to one target Equipment you control to that creature.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new KembaKhaEnduringEffect(), filter, false, true
        );
        ability.addTarget(new TargetPermanent(0, 1, filter2));
        this.addAbility(ability);

        // Equipped creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter3
        )));

        // {3}{W}{W}: Create a 2/2 white Cat creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new CatToken()), new ManaCostsImpl<>("{3}{W}{W}")
        ));
    }

    private KembaKhaEnduring(final KembaKhaEnduring card) {
        super(card);
    }

    @Override
    public KembaKhaEnduring copy() {
        return new KembaKhaEnduring(this);
    }
}

class KembaKhaEnduringEffect extends OneShotEffect {

    KembaKhaEnduringEffect() {
        super(Outcome.Benefit);
        staticText = "attach up to one target Equipment you control to that creature";
    }

    private KembaKhaEnduringEffect(final KembaKhaEnduringEffect effect) {
        super(effect);
    }

    @Override
    public KembaKhaEnduringEffect copy() {
        return new KembaKhaEnduringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && equipment != null
                && permanent.addAttachment(equipment.getId(), source, game);
    }
}
