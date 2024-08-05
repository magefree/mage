package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidnightArsonist extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VAMPIRE);
    private static final FilterPermanent filter2 = new FilterArtifactPermanent("artifacts without mana abilities");

    static {
        filter2.add(MidnightArsonistPredicate.instance);
    }

    public MidnightArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Midnight Arsonist enters the battlefield, destroy up to X target artifacts without mana abilities, where X is the number of Vampires you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect()
                .setText("destroy up to X target artifacts without mana abilities, where X is the number of Vampires you control"));
        ability.addTarget(new TargetPermanent(0, 1, filter2));
        ability.setTargetAdjuster(new TargetsCountAdjuster(new PermanentsOnBattlefieldCount(filter)));
        ability.addHint(new ValueHint("Vampires you control", new PermanentsOnBattlefieldCount(filter)));
        this.addAbility(ability);
    }

    private MidnightArsonist(final MidnightArsonist card) {
        super(card);
    }

    @Override
    public MidnightArsonist copy() {
        return new MidnightArsonist(this);
    }
}

enum MidnightArsonistPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAbilities(game).stream().noneMatch(ManaAbility.class::isInstance);
    }
}
