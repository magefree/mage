package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.s.SpellstutterSprite;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class GomaFadaVanguard extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent(
            "creature an opponent controls with power less than or equal to the number of Warriors you control"
    );

    static {
        filter.add(GomaFadaVanguardPredicate.instance);
    }

    public GomaFadaVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Goma Fada Vanguard attacks, target creature an opponent controls with power less than or equal to the number of Warriors you control can't block this turn.
        Ability ability = new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GomaFadaVanguard(final GomaFadaVanguard card) {
        super(card);
    }

    @Override
    public GomaFadaVanguard copy() {
        return new GomaFadaVanguard(this);
    }
}

enum GomaFadaVanguardPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<MageObject>> {
    instance;
    private static final FilterPermanent filter = new FilterPermanent(SubType.WARRIOR, "");

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input.getObject().getPower().getValue() <=
                game.getBattlefield().countAll(filter, game.getControllerId(input.getSourceId()), game);
    }
}
