package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChakramRetriever extends CardImpl {

    public ChakramRetriever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Partner with Chakram Slinger (When this creature enters the battlefield, target player may put Chakram Slinger into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Chakram Slinger"));

        // Whenever you cast a spell during your turn, untap target creature.
        Ability ability = new ConditionalTriggeredAbility(
                new SpellCastControllerTriggeredAbility(new UntapTargetEffect(), false),
                MyTurnCondition.instance,
                "Whenever you cast a spell during your turn, untap target creature."
        ).addHint(MyTurnHint.instance);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ChakramRetriever(final ChakramRetriever card) {
        super(card);
    }

    @Override
    public ChakramRetriever copy() {
        return new ChakramRetriever(this);
    }
}
