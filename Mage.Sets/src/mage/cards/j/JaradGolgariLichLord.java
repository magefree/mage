package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.assignment.common.SubTypeAssignment;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetSacrifice;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class JaradGolgariLichLord extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
    private static final Hint hint = new ValueHint("Creatures in your graveyard", xValue);

    public JaradGolgariLichLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Jarad, Golgari Lich Lord gets +1/+1 for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)).addHint(hint));

        // {1}{B}{G}, Sacrifice another creature: Each opponent loses life equal to the sacrificed creature's power.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(SacrificeCostCreaturesPower.instance)
                .setText("each opponent loses life equal to the sacrificed creature's power"), new ManaCostsImpl<>("{1}{B}{G}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);

        // Sacrifice a Swamp and a Forest: Return Jarad from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new SacrificeTargetCost(new JaradGolgariLichLordTarget())
        ));

    }

    private JaradGolgariLichLord(final JaradGolgariLichLord card) {
        super(card);
    }

    @Override
    public JaradGolgariLichLord copy() {
        return new JaradGolgariLichLord(this);
    }
}

class JaradGolgariLichLordTarget extends TargetSacrifice {

    private static final FilterPermanent filter = new FilterPermanent("a Swamp and a Forest");

    static {
        filter.add(Predicates.or(
                SubType.SWAMP.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    private static final SubTypeAssignment subtypeAssigner = new SubTypeAssignment(SubType.SWAMP, SubType.FOREST);

    JaradGolgariLichLordTarget() {
        super(2, 2, filter);
    }

    private JaradGolgariLichLordTarget(final JaradGolgariLichLordTarget target) {
        super(target);
    }

    @Override
    public JaradGolgariLichLordTarget copy() {
        return new JaradGolgariLichLordTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Set<Permanent> permanents = this
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        permanents.add(game.getPermanent(id));
        return subtypeAssigner.getRoleCount(permanents, game) >= permanents.size();
    }
}
