package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author muz
 */
public final class ReverendMotherJessica extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with lesser power");

    static {
        filter.add(ReverendMotherJessicaPredicate.instance);
    }

    public ReverendMotherJessica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Reverend Mother Jessica attacks, for each opponent, tap up to one target creature that player controls with lesser power and goad it.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect()
            .setText("for each opponent, tap up to one target creature that player controls with lesser power"));
        ability.addEffect(new GoadTargetEffect().setText("and goad it"));
        ability.getEffects().setTargetPointer(new EachTargetPointer());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);
    }

    private ReverendMotherJessica(final ReverendMotherJessica card) {
        super(card);
    }

    @Override
    public ReverendMotherJessica copy() {
        return new ReverendMotherJessica(this);
    }
}

enum ReverendMotherJessicaPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent source = input.getSource().getSourcePermanentOrLKI(game);
        return source != null && input.getObject().getPower().getValue() < source.getPower().getValue();
    }
}
