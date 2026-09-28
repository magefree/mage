package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TomikOrzhovLawmage extends CardImpl {

    public TomikOrzhovLawmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Planeswalkers you control have "No more than one creature can attack this planeswalker each combat."
        Ability gainedAbility = new SimpleStaticAbility(new TomikOrzhovLawmageEffect());
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            gainedAbility, Duration.WhileOnBattlefield,
            StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"No more than one creature can attack this planeswalker each combat.\"")));

        // {T}: Target creature with a +1/+1 counter on it gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
            new TapSourceCost()
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_P1P1));
        this.addAbility(ability);
    }

    private TomikOrzhovLawmage(final TomikOrzhovLawmage card) {
        super(card);
    }

    @Override
    public TomikOrzhovLawmage copy() {
        return new TomikOrzhovLawmage(this);
    }
}


class TomikOrzhovLawmageEffect extends RestrictionEffect {

    TomikOrzhovLawmageEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "No more than one creature can attack this planeswalker each combat";
    }

    private TomikOrzhovLawmageEffect(final TomikOrzhovLawmageEffect effect) {
        super(effect);
    }

    @Override
    public TomikOrzhovLawmageEffect copy() {
        return new TomikOrzhovLawmageEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {

        if(defenderId == null || attacker == null || source == null){
            return true;
        }

        // Check if attacking the planeswalker this effect applies to
        if(defenderId.equals(source.getSourceId())){

            // If there is already a creature attacking this planeswalker, don't let another creature attack it
            for(CombatGroup group : game.getCombat().getGroups()){
                if(group.getDefenderId() != null && group.getDefenderId().equals(source.getSourceId())){
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}
