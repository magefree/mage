package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KykarZephyrAwakener extends CardImpl {

    public KykarZephyrAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, choose one --
        // * Exile another target creature you control. Return it to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(false),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));

        // * Create a 1/1 white Spirit creature token with flying.
        ability.addMode(new Mode(new CreateTokenEffect(new SpiritWhiteToken())));
        this.addAbility(ability);
    }

    private KykarZephyrAwakener(final KykarZephyrAwakener card) {
        super(card);
    }

    @Override
    public KykarZephyrAwakener copy() {
        return new KykarZephyrAwakener(this);
    }
}
