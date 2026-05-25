package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.ModifyObjectAllMultiZoneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreatureSpell;
import mage.filter.common.FilterOwnedCreatureCard;
import mage.game.permanent.token.NecronWarriorToken;

import java.util.UUID;

public final class Biotransference extends CardImpl {

    public Biotransference(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Creatures you control are artifacts in addition to their other types. The same is true for creature spells you control and creature cards you own that aren’t on the battlefield.
        this.addAbility(new SimpleStaticAbility(new BiotransferenceEffect()));

        // Whenever you cast an artifact spell, you lose 1 life and create a 2/2 black Necron Warrior artifact creature token.
        Ability ability = new SpellCastControllerTriggeredAbility(new LoseLifeSourceControllerEffect(1), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false);
        ability.addEffect(new CreateTokenEffect(new NecronWarriorToken(), 1).concatBy("and"));
        this.addAbility(ability);
    }

    private Biotransference(final Biotransference card) {
        super(card);
    }

    @Override
    public Biotransference copy() {
        return new Biotransference(this);
    }
}

class BiotransferenceEffect extends ModifyObjectAllMultiZoneEffect {

    BiotransferenceEffect() {
        super(
            StaticFilters.FILTER_CONTROLLED_CREATURES,
            new FilterControlledCreatureSpell("creature spells you control"),
            new FilterOwnedCreatureCard("creature cards you own"),
            (obj, source, game) -> { game.getState().getCreateMageObjectAttribute(obj, game).getCardType().add(CardType.ARTIFACT); },
            CardType.ARTIFACT.getPluralName() + " in addition to their other types"
        );
        this.dependencyTypes.add(DependencyType.ArtifactAddingRemoving); // March of the Machines
    }

    private BiotransferenceEffect(final BiotransferenceEffect effect) {
        super(effect);
    }

    @Override
    public BiotransferenceEffect copy() {
        return new BiotransferenceEffect(this);
    }

}
