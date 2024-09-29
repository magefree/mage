package mage.cards.n;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.UndyingAbility;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Cguy7777
 */
public final class NardoleResourcefulCyborg extends CardImpl {

    public NardoleResourcefulCyborg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Add {U} for each counter on Nardole. Spend this mana only to cast noncreature spells.
        this.addAbility(new SimpleManaAbility(new NardoleResourcefulCyborgManaEffect(), new TapSourceCost()));

        // Undying
        this.addAbility(new UndyingAbility());

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private NardoleResourcefulCyborg(final NardoleResourcefulCyborg card) {
        super(card);
    }

    @Override
    public NardoleResourcefulCyborg copy() {
        return new NardoleResourcefulCyborg(this);
    }
}

class NardoleResourcefulCyborgManaEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder
            = new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELLS_NON_CREATURE);

    NardoleResourcefulCyborgManaEffect() {
        this.staticText = "Add {U} for each counter on {this}. " + manaBuilder.getRule();
    }

    private NardoleResourcefulCyborgManaEffect(final NardoleResourcefulCyborgManaEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent != null) {
            netMana.add(manaBuilder.setMana(
                    Mana.BlueMana(permanent.getCounters(game).getTotalCount()), source, game).build());
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return mana;
        }
        return manaBuilder.setMana(
                Mana.BlueMana(permanent.getCounters(game).getTotalCount()), source, game).build();
    }

    @Override
    public NardoleResourcefulCyborgManaEffect copy() {
        return new NardoleResourcefulCyborgManaEffect(this);
    }
}
