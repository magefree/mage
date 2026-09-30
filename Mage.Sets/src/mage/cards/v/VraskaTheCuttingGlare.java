package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class VraskaTheCuttingGlare extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterLandPermanent("you control six or more lands"), 
        ComparisonType.OR_GREATER, 6
    );

    public VraskaTheCuttingGlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Vraska enters, if you control six or more lands, destroy target permanent an opponent controls. They create a Treasure token.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new VraskaTheCuttingGlareEffect()
        ).withInterveningIf(condition).addHint(LandsYouControlHint.instance);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT));
        this.addAbility(ability);
    }

    private VraskaTheCuttingGlare(final VraskaTheCuttingGlare card) {
        super(card);
    }

    @Override
    public VraskaTheCuttingGlare copy() {
        return new VraskaTheCuttingGlare(this);
    }
}


class VraskaTheCuttingGlareEffect extends OneShotEffect {

    VraskaTheCuttingGlareEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target permanent an opponent controls. They create a Treasure token";
    }

    private VraskaTheCuttingGlareEffect(final VraskaTheCuttingGlareEffect effect) {
        super(effect);
    }

    @Override
    public VraskaTheCuttingGlareEffect copy() {
        return new VraskaTheCuttingGlareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }

        permanent.destroy(source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            new TreasureToken().putOntoBattlefield(1, game, source, permanent.getControllerId());
            return true;
        }

        return false;
    }
}
