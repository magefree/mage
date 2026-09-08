package mage.cards.w;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.mana.conditional.ManaCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;

/**
 *
 * @author muz
 */
public final class WoodlandWeavemaster extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELF, "another Elf you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WoodlandWeavemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever another Elf you control enters, this creature gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter
        ));

        // {T}: Add X mana of any one color, where X is this creature's power. Spend this mana only to cast Elf spells and activate abilities of Elf sources.
        this.addAbility(new ConditionalAnyColorManaAbility(
            new TapSourceCost(),
            SourcePermanentPowerValue.NOT_NEGATIVE,
            SourcePermanentPowerValue.NOT_NEGATIVE,
            new WoodlandWeavemasterManaBuilder(),
            true
        ));
    }

    private WoodlandWeavemaster(final WoodlandWeavemaster card) {
        super(card);
    }

    @Override
    public WoodlandWeavemaster copy() {
        return new WoodlandWeavemaster(this);
    }
}

class WoodlandWeavemasterManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new WoodlandWeavemasterConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Elf spells and activate abilities of Elf sources";
    }
}

class WoodlandWeavemasterConditionalMana extends ConditionalMana {

    WoodlandWeavemasterConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast Elf spells and activate abilities of Elf sources";
        addCondition(new WoodlandWeavemasterManaCondition());
    }
}

class WoodlandWeavemasterManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object != null && object.hasSubtype(SubType.ELF, game)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
