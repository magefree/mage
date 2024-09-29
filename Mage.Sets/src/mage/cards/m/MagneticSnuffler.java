package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author DominionSpy
 */
public final class MagneticSnuffler extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment card from your graveyard");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public MagneticSnuffler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Magnetic Snuffler enters the battlefield, return target Equipment card from your graveyard to the battlefield attached to Magnetic Snuffler.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MagneticSnufflerEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Whenever you sacrifice an artifact, put a +1/+1 counter on Magnetic Snuffler.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_PERMANENT_ARTIFACT));
    }

    private MagneticSnuffler(final MagneticSnuffler card) {
        super(card);
    }

    @Override
    public MagneticSnuffler copy() {
        return new MagneticSnuffler(this);
    }
}

class MagneticSnufflerEffect extends ReturnFromGraveyardToBattlefieldTargetEffect {

    MagneticSnufflerEffect() {
        super();
        staticText = "return target Equipment card from your graveyard to the battlefield attached to {this}";
    }

    private MagneticSnufflerEffect(final MagneticSnufflerEffect effect) {
        super(effect);
    }

    @Override
    public MagneticSnufflerEffect copy() {
        return new MagneticSnufflerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        super.apply(game, source);

        Permanent equipment = game.getPermanent(source.getFirstTarget());
        Permanent creature = game.getPermanent(source.getSourceId());
        if (equipment == null || creature == null) {
            return false;
        }

        return creature.addAttachment(equipment.getId(), source, game);
    }
}
