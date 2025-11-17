package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokkaSwordmaster extends CardImpl {

    public SokkaSwordmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Equipment spells you cast cost {1} less to cast for each Ally you control.
        this.addAbility(new SimpleStaticAbility(new SokkaSwordmasterEffect()).addHint(AffinityType.ALLIES.getHint()));

        // At the beginning of combat on your turn, attach up to one target Equipment you control to Sokka.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AttachEffect(Outcome.BoostCreature, "attach up to one target Equipment you control to {this}"
                ));
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT
        ));
        this.addAbility(ability);
    }

    private SokkaSwordmaster(final SokkaSwordmaster card) {
        super(card);
    }

    @Override
    public SokkaSwordmaster copy() {
        return new SokkaSwordmaster(this);
    }
}

class SokkaSwordmasterEffect extends CostModificationEffectImpl {

    SokkaSwordmasterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Equipment spells you cast cost {1} less to cast for each Ally you control";
    }

    private SokkaSwordmasterEffect(final SokkaSwordmasterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, game.getBattlefield().count(AffinityType.ALLIES.getFilter(), source.getControllerId(), source, game));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return Optional
                .ofNullable(abilityToModify)
                .filter(SpellAbility.class::isInstance)
                .filter(spellAbility -> spellAbility.isControlledBy(source.getControllerId()))
                .map(SpellAbility.class::cast)
                .map(spellAbility -> spellAbility.getCharacteristics(game))
                .filter(card -> card.hasSubtype(SubType.EQUIPMENT, game))
                .isPresent();
    }

    @Override
    public SokkaSwordmasterEffect copy() {
        return new SokkaSwordmasterEffect(this);
    }

}
