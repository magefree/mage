package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MurderousRider extends AdventureCard {

    public MurderousRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{B}{B}", "Swift End", "{1}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Murderous Rider dies, put it on the bottom of its owner's library.
        this.addAbility(new DiesSourceTriggeredAbility(new PutOnLibrarySourceEffect(
                false, "put it on the bottom of its owner's library"
        ), false));

        // Swift End
        // Destroy target creature or planeswalker. You lose 2 life.
        this.getSpellCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellCard().getSpellAbility().addEffect(
                new LoseLifeSourceControllerEffect(2).setText("You lose 2 life.")
        );
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        this.finalizeAdventure();
    }

    private MurderousRider(final MurderousRider card) {
        super(card);
    }

    @Override
    public MurderousRider copy() {
        return new MurderousRider(this);
    }
}
