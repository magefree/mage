package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.RevealAndSeparatePilesEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class UneshCriosphinxSovereign extends CardImpl {

    private static final FilterCard filter = new FilterCard("Sphinx spells");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.SPHINX, "Sphinx");

    static {
        filter.add(SubType.SPHINX.getPredicate());
    }

    public UneshCriosphinxSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sphinx spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)));

        // Whenever Unesh, Criosphinx Sovereign or another Sphinx enters the battlefield under your control, reveal the top four cards of your library. An opponent seperates those cards into two piles. Put one pile into your hand and the other into your graveyard.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new RevealAndSeparatePilesEffect(
                4, TargetController.OPPONENT, TargetController.YOU, Zone.GRAVEYARD
        ), filter2, false, true));
    }

    private UneshCriosphinxSovereign(final UneshCriosphinxSovereign card) {
        super(card);
    }

    @Override
    public UneshCriosphinxSovereign copy() {
        return new UneshCriosphinxSovereign(this);
    }
}
