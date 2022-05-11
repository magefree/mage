package mage.cards.g;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GnarlrootTrapper extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("attacking ELf you control");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(SubType.ELF.getPredicate());
    }

    public GnarlrootTrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}, Pay 1 life: Add {G}. Spend this mana only to cast an Elf creature spell.
        Ability ability = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.GreenMana(1), new GnarlrootTrapperManaBuilder());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {T}: Target attacking Elf you control gains deathtouch until end of turn.
        Effect effect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Target attacking Elf you control gains deathtouch until end of turn. <i>(Any amount of damage it deals to a creature is enough to destroy it.)</i>");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);

    }

    private GnarlrootTrapper(final GnarlrootTrapper card) {
        super(card);
    }

    @Override
    public GnarlrootTrapper copy() {
        return new GnarlrootTrapper(this);
    }
}

class GnarlrootTrapperManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new GnarlrootTrapperConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an Elf creature spell.";
    }
}

class GnarlrootTrapperManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (super.apply(game, source)) {
            MageObject object = game.getObject(source);
            return object != null && object.hasSubtype(SubType.ELF, game)
                    && object.isCreature(game);
        }
        return false;
    }
}

class GnarlrootTrapperConditionalMana extends ConditionalMana {

    public GnarlrootTrapperConditionalMana(Mana mana) {
        super(mana);
        addCondition(new GnarlrootTrapperManaCondition());
    }
}
