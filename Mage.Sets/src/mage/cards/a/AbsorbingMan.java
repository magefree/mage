package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;

/**
 *
 * @author muz
 */
public final class AbsorbingMan extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, non-Aura enchantment, or land");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            Predicates.and(
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.not(SubType.AURA.getPredicate())
            ),
            CardType.LAND.getPredicate()
        ));
    }

    public AbsorbingMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your first main phase, until your next turn, Absorbing Man becomes a copy of up to one target artifact, non-Aura enchantment, or land, except his name is Absorbing Man, he's a legendary 4/4 Human Villain creature in addition to his other types, and he has vigilance.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(new CopyPermanentEffect(
                filter, new AbsorbingManCopyApplier(), true
        ).setDuration(Duration.UntilYourNextTurn).setText(
                "until your next turn, {this} becomes a copy of up to one target artifact, non-Aura enchantment, or land, "
                    + "except his name is Absorbing Man, he's a legendary 4/4 Human Villain creature in addition to his other types, "
                    + "and he has vigilance"
        ));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private AbsorbingMan(final AbsorbingMan card) {
        super(card);
    }

    @Override
    public AbsorbingMan copy() {
        return new AbsorbingMan(this);
    }
}

class AbsorbingManCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.setName("Absorbing Man");
        blueprint.addSuperType(SuperType.LEGENDARY);
        blueprint.addCardType(game, CardType.CREATURE);
        blueprint.removePTCDA();
        blueprint.getPower().setModifiedBaseValue(4);
        blueprint.getToughness().setModifiedBaseValue(4);
        blueprint.addSubType(SubType.HUMAN, SubType.VILLAIN);
        blueprint.getAbilities().add(VigilanceAbility.getInstance());
        return true;
    }
}
