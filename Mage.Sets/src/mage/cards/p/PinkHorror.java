package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BlueHorrorToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PinkHorror extends CardImpl {

    public PinkHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Coruscating Flames -- Whenever you cast an instant or sorcery spell, Pink Horror deals 2 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamageTargetEffect(2),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.withFlavorWord("Coruscating Flames"));

        // Split -- When Pink Horror dies, create two 2/2 blue and red Demon Horror creature tokens named Blue Horror with "Whenever you cast an instant or sorcery spell, this creature deals 1 damage to any target."
        this.addAbility(new DiesSourceTriggeredAbility(
                new CreateTokenEffect(new BlueHorrorToken(), 2)
        ).withFlavorWord("Split"));
    }

    private PinkHorror(final PinkHorror card) {
        super(card);
    }

    @Override
    public PinkHorror copy() {
        return new PinkHorror(this);
    }
}
