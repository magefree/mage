package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class LizardConnorssCurse extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public LizardConnorssCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lizard Formula -- When Lizard, Connors's Curse enters, up to one other target creature loses all abilities and becomes a green Lizard creature with base power and toughness 4/4.
        CreatureToken token = new CreatureToken(4, 4, "green Lizard creature with base power and toughness 4/4", SubType.LIZARD)
                .withColor("G");
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BecomesCreatureTargetEffect(token, true, false, Duration.Custom)
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.withFlavorWord("Lizard Formula");
        this.addAbility(ability);
    }

    private LizardConnorssCurse(final LizardConnorssCurse card) {
        super(card);
    }

    @Override
    public LizardConnorssCurse copy() {
        return new LizardConnorssCurse(this);
    }
}
