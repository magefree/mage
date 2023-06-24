package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaslaTheBrokenHalo extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("another spell that has convoke");

    static {
        filter.add(new AbilityPredicate(ConvokeAbility.class));
    }

    public KaslaTheBrokenHalo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you cast another spell that has convoke, scry 2, then draw a card.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new ScryEffect(2, false), filter, false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);
    }

    private KaslaTheBrokenHalo(final KaslaTheBrokenHalo card) {
        super(card);
    }

    @Override
    public KaslaTheBrokenHalo copy() {
        return new KaslaTheBrokenHalo(this);
    }
}
