package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DizzyingGaze extends CardImpl {

    public DizzyingGaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // {R}: Enchanted creature deals 1 damage to target creature with flying.
        Ability ability = new SimpleActivatedAbility(new DizzyingGazeEffect(), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private DizzyingGaze(final DizzyingGaze card) {
        super(card);
    }

    @Override
    public DizzyingGaze copy() {
        return new DizzyingGaze(this);
    }
}

class DizzyingGazeEffect extends OneShotEffect {

    DizzyingGazeEffect() {
        super(Outcome.Benefit);
        staticText = "enchanted creature deals 1 damage to target creature with flying";
    }

    private DizzyingGazeEffect(final DizzyingGazeEffect effect) {
        super(effect);
    }

    @Override
    public DizzyingGazeEffect copy() {
        return new DizzyingGazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        return creature != null
                && Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .filter(permanentId -> creature.damage(1, permanentId, source, game) > 0)
                .isPresent();
    }
}
