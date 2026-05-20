package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiblioplexTomekeeper extends CardImpl {

    public BiblioplexTomekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, choose up to one --
        // * Target creature becomes prepared.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BecomePreparedTargetEffect(true));
        ability.addTarget(new TargetCreaturePermanent());
        ability.getModes().setMinModes(0);

        // * Target creature becomes unprepared.
        ability.addMode(new Mode(new BecomePreparedTargetEffect(false)).addTarget(new TargetCreaturePermanent()));
        this.addAbility(ability);
    }

    private BiblioplexTomekeeper(final BiblioplexTomekeeper card) {
        super(card);
    }

    @Override
    public BiblioplexTomekeeper copy() {
        return new BiblioplexTomekeeper(this);
    }
}
