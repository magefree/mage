package mage.cards.r;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInExile;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootcoilCreeper extends CardImpl {

    private static final FilterCard filter = new FilterOwnedCard("card with flashback you own from exile");

    static {
        filter.add(new AbilityPredicate(FlashbackAbility.class));
    }

    public RootcoilCreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Add two mana of any one color. Spend this mana only to cast spells from your graveyard.
        this.addAbility(new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 2, new RootcoilCreeperManaBuilder(), true
        ));

        // {G}{U}, {T}, Exile Rootcoil Creeper: Return target card with flashback you own in exile to your hand.
        Ability ability = new SimpleActivatedAbility(
                new ReturnToHandTargetEffect()
                        .setText("return target card with flashback you own from exile to your hand"),
                new ManaCostsImpl<>("{G}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetCardInExile(filter));
        this.addAbility(ability);
    }

    private RootcoilCreeper(final RootcoilCreeper card) {
        super(card);
    }

    @Override
    public RootcoilCreeper copy() {
        return new RootcoilCreeper(this);
    }
}

class RootcoilCreeperManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new RootcoilCreeperConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast spells from your graveyard";
    }
}

class RootcoilCreeperConditionalMana extends ConditionalMana {

    public RootcoilCreeperConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast spells from your graveyard";
        addCondition(RootcoilCreeperManaCondition.instance);
    }
}

enum RootcoilCreeperManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(source);
        if (!source.isControlledBy(game.getOwnerId(object))) {
            return false;
        }
        if (object instanceof Spell) {
            return ((Spell) object).getFromZone() == Zone.GRAVEYARD;
        }
        // checking mana without real cast
        return game.inCheckPlayableState() && game.getState().getZone(object.getId()) == Zone.GRAVEYARD;
    }
}
