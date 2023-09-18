package mage.cards.v;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.Mana;
import mage.abilities.SpellAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

public final class VhalCandlekeepResearcher extends CardImpl {

    public VhalCandlekeepResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Add an amount of {C} equal to Vhal, Candlekeep Researcher's toughness. This mana can't be spent to cast spells from your hand.
        this.addAbility(
                new SimpleManaAbility(Zone.BATTLEFIELD, new VhalCandlekeepResearcherManaEffect(), new TapSourceCost()));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private VhalCandlekeepResearcher(final VhalCandlekeepResearcher card) {
        super(card);
    }

    @Override
    public VhalCandlekeepResearcher copy() {
        return new VhalCandlekeepResearcher(this);
    }
}

class VhalCandlekeepResearcherManaEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder = new VhalCandlekeepResearcherManaBuilder();

    VhalCandlekeepResearcherManaEffect() {
        super();
        this.staticText = "Add an amount of {C} equal to {this}'s toughness. This mana can't be spent to cast spells from your hand.";
    }

    private VhalCandlekeepResearcherManaEffect(final VhalCandlekeepResearcherManaEffect effect) {
        super(effect);
    }

    @Override
    public VhalCandlekeepResearcherManaEffect copy() {
        return new VhalCandlekeepResearcherManaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        int calculatedToughness = (sourcePermanent == null ? 0 : sourcePermanent.getToughness().getValue());
        return manaBuilder.setMana(Mana.ColorlessMana(calculatedToughness), source, game).build();
    }
}

class VhalCandlekeepResearcherManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new VhalCandlekeepResearcherConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "This mana can't be spent to cast spells from your hand";
    }
}

class VhalCandlekeepResearcherConditionalMana extends ConditionalMana {

    VhalCandlekeepResearcherConditionalMana(Mana mana) {
        super(mana);
        staticText = "This mana can't be spent to cast spells from your hand";
        addCondition(VhalCandlekeepResearcherManaCondition.instance);
    }
}

enum VhalCandlekeepResearcherManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return true;
        }
        MageObject object = game.getObject(source);
        if (!source.isControlledBy(game.getOwnerId(object))) {
            return false;
        }
        if (object instanceof Spell) {
            return ((Spell) object).getFromZone() != Zone.HAND;
        }
        // checking mana without real cast
        return game.inCheckPlayableState() && game.getState().getZone(source.getSourceId()) != Zone.HAND;
    }
}
