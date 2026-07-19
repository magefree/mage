package mage.cards.w;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author anonymous
 */
public final class WinterSoldierRebornAvenger extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value less than or equal to {this}'s power from your graveyard"
    );

    static {
        filter.add(WinterSoldierRebornAvengerPredicate.instance);
    }

    public WinterSoldierRebornAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever Winter Soldier attacks, return target creature card with mana value less than or equal to Winter
        // Soldier's power from your graveyard to the battlefield. If a Hero enters this way, it enters with an
        // additional +1/+1 counter on it.
        Ability ability = new AttacksTriggeredAbility(new WinterSoldierRebornAvengerEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private WinterSoldierRebornAvenger(final WinterSoldierRebornAvenger card) {
        super(card);
    }

    @Override
    public WinterSoldierRebornAvenger copy() {
        return new WinterSoldierRebornAvenger(this);
    }
}

class WinterSoldierRebornAvengerEffect extends ReturnFromGraveyardToBattlefieldTargetEffect {
    private static final FilterCard filter = new FilterCreatureCard("a Hero");

    static {
        filter.add(SubType.HERO.getPredicate());
    }
    WinterSoldierRebornAvengerEffect() {
        super();
    }

    private WinterSoldierRebornAvengerEffect(final WinterSoldierRebornAvengerEffect effect) {
        super(effect);
    }

    @Override
    public WinterSoldierRebornAvengerEffect copy() {
        return new WinterSoldierRebornAvengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            if (filter.match(game.getCard(targetId), source.getControllerId(), source, game)) {
                game.setEnterWithCounters(targetId, new Counters().addCounter(CounterType.P1P1.createInstance(1)));
            }
        }
        return super.apply(game, source);
    }

    @Override
    public String getText(Mode mode) {
        String text = super.getText(mode);
        return text.concat(". If a Hero enters this way, it enters with an additional +1/+1 counter on it.");
    }
}

enum WinterSoldierRebornAvengerPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(p -> input.getObject().getManaValue() <= p.getValue())
                .orElse(false);
    }
}
