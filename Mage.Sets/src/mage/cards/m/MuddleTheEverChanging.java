package mage.cards.m;

import java.util.UUID;


import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class MuddleTheEverChanging extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nonlegendary creature you control");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public MuddleTheEverChanging(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell, Muddle becomes a copy of up to one target nonlegendary creature you control until end of turn, except it has myriad.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new MuddleTheEverChangingCopySelfEffect(),
            StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private MuddleTheEverChanging(final MuddleTheEverChanging card) {
        super(card);
    }

    @Override
    public MuddleTheEverChanging copy() {
        return new MuddleTheEverChanging(this);
    }
}

class MuddleTheEverChangingCopySelfEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
            blueprint.getAbilities().add(new MyriadAbility());
            return true;
        }
    };

    MuddleTheEverChangingCopySelfEffect() {
        super(Outcome.Benefit);
        staticText = "{this} becomes a copy of up to one target nonlegendary creature " +
            "you control until end of turn, except it has myriad";
    }

    private MuddleTheEverChangingCopySelfEffect(final MuddleTheEverChangingCopySelfEffect effect) {
        super(effect);
    }

    @Override
    public MuddleTheEverChangingCopySelfEffect copy() {
        return new MuddleTheEverChangingCopySelfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || creature == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, creature, permanent.getId(), source, applier);
        return true;
    }
}
